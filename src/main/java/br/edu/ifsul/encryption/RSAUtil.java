package br.edu.ifsul.encryption;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class RSAUtil {

	private final BigInteger expoenteDeEncriptacao;
	private final BigInteger modulo;
	private final int tamanhoDoBloco;

	public RSAUtil(BigInteger expoenteDeEncriptacao, BigInteger modulo) {

		this.expoenteDeEncriptacao = expoenteDeEncriptacao;
		this.modulo = modulo;
		this.tamanhoDoBloco = 14;
		
	}
	
	public String encriptarString(String string) {
	
		List<Integer> array = new LinkedList<>(); 
		
		for (int i = 0; i < string.length(); i++)
			array.add(string.codePointAt(i));
		
		while (array.size() % tamanhoDoBloco != 0)
			array.add(0);
		
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < array.size(); i += tamanhoDoBloco) {
			
			byte[] bloco = new byte[tamanhoDoBloco];
			
			int j = tamanhoDoBloco - 1;
			for (int k = i; k < i + tamanhoDoBloco; j--) {
				bloco[j] = array.get(k++).byteValue();
			}
			
			BigInteger bigNumber = new BigInteger(bloco);
			BigInteger modPow = bigNumber.modPow(expoenteDeEncriptacao, modulo);
			result.append(modPow.toString(16)).append(" ");
			
		}
		
		return result.toString();
	}
	
}
