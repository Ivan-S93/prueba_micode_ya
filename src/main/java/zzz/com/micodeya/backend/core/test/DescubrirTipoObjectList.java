package zzz.com.micodeya.backend.core.test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import zzz.com.micodeya.backend.core.entities.zk.Usuario;

public class DescubrirTipoObjectList {
    
    public static void main(String[] args) throws NoSuchFieldException, SecurityException {
         
         Usuario obj= new Usuario();


         String claseDeList=getListType(obj, "usuarioRolList");

         System.out.println("claseDeList= "+claseDeList);
      

          

		

    }


    public static  String getListType(Object obj, String atributo) throws NoSuchFieldException, SecurityException{


        
        Class<? extends Object> testClass = obj.getClass();

        Field listField = testClass.getDeclaredField(atributo);
        ParameterizedType listType = (ParameterizedType) listField.getGenericType();
        Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
        //System.out.println(listClass); 

        return listClass.getCanonicalName();
 

    }
}
