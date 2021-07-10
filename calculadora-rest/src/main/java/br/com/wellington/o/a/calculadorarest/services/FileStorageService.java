package br.com.wellington.o.a.calculadorarest.services;

import br.com.wellington.o.a.calculadorarest.config.FileStorageConfig;
import br.com.wellington.o.a.calculadorarest.exception.FileNotFindCustomException;
import br.com.wellington.o.a.calculadorarest.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception exception) {
			throw new FileStorageException("Não foi possível criar pasta de upload", exception);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Nome do arquivo invalido " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (Exception exception) {
			throw new FileStorageException("Não foi possível salvar o arquivo " + fileName, exception);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			}
			throw new FileNotFindCustomException("Arquivo não ecnotrado" + fileName);
		} catch (Exception exception) {
			throw new FileNotFindCustomException("Arquivo não ecnotrado" + fileName, exception);
		}
	}
}
