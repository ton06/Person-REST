package br.com.wellington.o.a.calculadorarest.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountCredentialsVO implements Serializable {

	private String userName;
	private String passwords;
}
