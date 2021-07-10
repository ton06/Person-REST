package br.com.wellington.o.a.calculadorarest.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.OperationNotSupportedException;

@RestController
@RequestMapping(value = "/math")
public class CalculadoraController {

	@GetMapping("sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws OperationNotSupportedException {
		try {
			var n1 = Double.parseDouble(numberOne);
			var n2 = Double.parseDouble(numberTwo);
			return Double.sum(n1, n2);
		} catch (NumberFormatException exception) {
			throw new OperationNotSupportedException("Valor invalido");
		}
	}

	@GetMapping("mult/{numberOne}/{numberTwo}")
	public Double mult(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws OperationNotSupportedException {
		try {
			var n1 = Double.parseDouble(numberOne);
			var n2 = Double.parseDouble(numberTwo);
			return n1 * n2;
		} catch (NumberFormatException exception) {
			throw new OperationNotSupportedException("Valor invalido");
		}
	}

	@GetMapping("div/{numberOne}/{numberTwo}")
	public Double div(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws OperationNotSupportedException {
		try {
			var n1 = Double.parseDouble(numberOne);
			var n2 = Double.parseDouble(numberTwo);
			return n1 / n2;
		} catch (NumberFormatException exception) {
			throw new OperationNotSupportedException("Valor invalido");
		}
	}

	@GetMapping("avg/{numberOne}/{numberTwo}")
	public Double avg(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws OperationNotSupportedException {
		try {
			var n1 = Double.parseDouble(numberOne);
			var n2 = Double.parseDouble(numberTwo);
			return (n1 + n2) / 2;
		} catch (NumberFormatException exception) {
			throw new OperationNotSupportedException("Valor invalido");
		}
	}

	@GetMapping("sqrroot/{numberOne}")
	public Double sqrroot(@PathVariable("numberOne") String numberOne) throws OperationNotSupportedException {
		try {
			var n1 = Double.parseDouble(numberOne);
			return Math.sqrt(n1);
		} catch (NumberFormatException exception) {
			throw new OperationNotSupportedException("Valor invalido");
		}
	}
}
