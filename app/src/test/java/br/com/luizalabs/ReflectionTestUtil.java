package br.com.luizalabs;


import java.lang.reflect.Field;

public class ReflectionTestUtil {

    public static <T> T getPrivateField(Object parent, String attribute){
        Field field = null;
        try {
            field = parent.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            return (T) field.get(parent);

        } catch (NoSuchFieldException e) {
            return getPrivateField(parent, parent.getClass().getSuperclass(), attribute);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T getPrivateField(Object parent, Class clazz, String attribute){
        Field field = null;
        try {
            field = clazz.getDeclaredField(attribute);
            field.setAccessible(true);
            return (T) field.get(parent);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setPrivateField(Object parent, String attribute, Object value){
        Field field = null;
        try {
            field = parent.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            field.set(parent, value);

        } catch (NoSuchFieldException ignored) {
            setPrivateField(parent, parent.getClass().getSuperclass(),attribute, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setPrivateField(Object parent,Class clazz, String attribute, Object value){
        Field field = null;
        try {
            field = clazz.getDeclaredField(attribute);
            field.setAccessible(true);
            field.set(parent, value);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
