package br.com.wellington.o.a.calculadorarest.data.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadFileResponseVO {

	private String filName;

	private String fileDownloadUri;

	private String fileType;

	private Long size;
}
