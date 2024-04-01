package com.xhpcd.protocol;

import com.google.common.escape.Escaper;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2023/12/11/12:31
 * @Description:
 */
public interface Serializer {
    //反序列化
    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException, ClassNotFoundException;
    <T> byte[] serialize(T object) throws IOException;

    enum Algorithm implements Serializer{
       JAVA{
           @Override
           public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException, ClassNotFoundException {

               ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
               ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
               return (T)objectInputStream.readObject();
           }

           @Override
           public <T> byte[] serialize(T object) throws IOException {
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
               objectOutputStream.writeObject(object);
               return bos.toByteArray();
           }
       },

        JSON{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException, ClassNotFoundException {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new adapter()).create();
                String json = new String(bytes,StandardCharsets.UTF_8);
                T t = gson.fromJson(json, clazz);
                return t;
            }

            @Override
            public <T> byte[] serialize(T object) throws IOException {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new adapter()).create();
                byte[] bytes = gson.toJson(object).getBytes(StandardCharsets.UTF_8);
                return bytes;
            }
        }


    }
     class adapter implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>>{

         @Override
         public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
             try {

                 String asString = jsonElement.getAsString();
                 return Class.forName(asString);
             }catch (Exception e){
                 throw new JsonParseException(e);
             }

         }

         @Override
         public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext jsonSerializationContext) {

             return new JsonPrimitive(aClass.getName());
         }
     }
}
