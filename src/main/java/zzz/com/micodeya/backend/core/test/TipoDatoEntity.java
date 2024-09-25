package zzz.com.micodeya.backend.core.test;

import java.util.LinkedList;
import java.util.List;

import zzz.com.micodeya.backend.core.entities.zk.Usuario;
import zzz.com.micodeya.backend.core.entities.zk.UsuarioRol;
// import zzz.com.micodeya.backend.core.entities.zk.RolRecurso;
import zzz.com.micodeya.backend.core.util.AtributoEntity;
import zzz.com.micodeya.backend.core.util.JeBoot;

public class TipoDatoEntity {
	/**
	 * 
	 * Para probar listar los atributos de un entity
	 */
    public static void main(String[] args) {

		try {
            Usuario ejemplo = new Usuario();
			//ejemplo.setUsuarioRolList(new LinkedList<UsuarioRol>());
			// RolRecurso rolRecurso =new RolRecurso();
			// rolRecurso.setRol(rolEjemplo);

            List<AtributoEntity> listAtributo= JeBoot.getAtributosValor(ejemplo,true);

        	System.out.println(listAtributo);




		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



    
}


