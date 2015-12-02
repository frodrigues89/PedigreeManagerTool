/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import control.PedToolControl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cluster
 */
public class DaoLerListaArquivos {

    public String ler(String caminhoArquivo) throws IOException, Exception {
        
        List<String> lista = new ArrayList();
        
        try{
			BufferedReader br = new BufferedReader(new FileReader(new File(caminhoArquivo)));
                           
			while(br.ready()){
                                
				lista.add(br.readLine());
                                 
			}

			br.close();
		}catch(IOException e){
			throw new IOException("An error occurred when performing a file is read:\n" + caminhoArquivo + "\n" + e.getMessage());
		}
        return geraString(lista);
        
    }

    private String geraString(List<String> lista) throws Exception {
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        PedToolControl ptc = new PedToolControl();
        while (i < lista.size()){
            //String[] separado = ptc.separaTexto(lista.get(i));
            //buffer.append(separado[0]);
            buffer.append(lista.get(i));
            i++;
            if (i+1<lista.size())
                    buffer.append(";");
        }
        return buffer.toString();
    }
        
    
    
    
    
}
