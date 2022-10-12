package com.wizeline.maven.learningjava;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

import com.wizeline.maven.learningjava.model.BankAccountModel;
import com.wizeline.maven.learningjava.model.ResponseModel;
import com.wizeline.maven.learningjava.service.BankAccountService;
import com.wizeline.maven.learningjava.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjava.service.UserService;
import com.wizeline.maven.learningjava.service.UserServiceImpl;
import com.wizeline.maven.learningjava.utils.excepcion.ExceptionGenerica;

@SuppressWarnings("unchecked")
public class LearningJavaApplication extends Thread{
	
private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());
private static String SUCCESS_CODE = "OK000";


private static String responseTextThread = "";
private ResponseModel response;
private static String textThread = "";


@EnableKafka
@EnableFeignClients
@SpringBootApplication
public static void main(String[] args) throws IOException {
}
//	LOGGER.info("LearningJava - Iniciado servicio REST ...");
	
	
	//servidor http simple
//	HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
	
//	server.createContext("/api/login", (exchange -> {
  // 	LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
    //	ResponseModel response;
    //	String responseText = "";
    	
    	
    	//validando que sea del tipo de solicitud http esperado
   //     if ("GET".equals(exchange.getRequestMethod())) {
     //   	LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
       // 	UserModel user =  new UserModel();
 //       	user = user.getParameters(splitQuery(exchange.getRequestURI()));
 //       	response = login(user.getUser(), user.getPassword());
 //       	JSONObject json = new JSONObject(response);
 //           responseText = json.toString();
 //           exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
   //         exchange.sendResponseHeaders(200, responseText.getBytes().length);
 //       } else {
        	
            //manda error de metodo no permitido
   //     	exchange.sendResponseHeaders(405, -1);
  //      }
 /*       OutputStream output = exchange.getResponseBody();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        output.write(responseText.getBytes());
        output.flush();
        output.close();
        exchange.close();
    }));
    
    
    server.createContext("/api/createUser", (exchange -> {
    	LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
    	ResponseModel response;
    	String responseText = "";
    	
    	exchange.getRequestBody();
        if ("POST".equals(exchange.getRequestMethod())) {
        	LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        	UserModel user =  new UserModel();
        	user = user.getParameters(splitQuery(exchange.getRequestURI()));
        	response = createUser(user.getUser(), user.getPassword());
        	JSONObject json = new JSONObject(response);
            responseText = json.toString();
            exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, responseText.getBytes().length);
        } else {
        	
        	exchange.sendResponseHeaders(405, -1);
        }
        OutputStream output = exchange.getResponseBody();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        output.write(responseText.getBytes());
        output.flush();
        output.close();
        exchange.close();
    }));
     		server.createContext("/api/getUserAccount", (exchange -> {
 			LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
 			
 			Instant inicioDeEjecucion = Instant.now();//crea un obejto de tipo instant con la hora actual
			ResponseModel response;
 			
 			String responseText = "";
 			
 			if ("GET".equals(exchange.getRequestMethod())) {
 				LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
 				UserModel user =  new UserModel();
 				Map<String, String> params = splitQuery(exchange.getRequestURI());
 				user = user.getParameters(params);
 				// Valida formato del parametro fecha (date) [dd-mm-yyyy]
 				String lastUsage = params.get("date");
 				System.out.println("lastUsage: "+lastUsage);
 				if (isDateFormatValid(lastUsage)) {
 					// Valida el password del usuario (password)
 				if (!isPasswordValid(user.getPassword())) {
 						response = login(user.getUser(), user.getPassword());
 						if (response.getCode().equals(SUCCESS_CODE)) {
 							BankAccountModel BankAccountModel = getAccountDetails(user.getUser(), lastUsage);
 							JSONObject json = new JSONObject(BankAccountModel);
 							responseText = json.toString();
 							exchange.getResponseHeaders().add("Content-type", "application/json");
 							exchange.sendResponseHeaders(200, responseText.getBytes().length);
 						}
 					} else {
 						responseText = "Password Incorrecto...";
 						exchange.getResponseHeaders().add("Content-type", "application/json");
 						exchange.sendResponseHeaders(401, responseText.getBytes().length);
 					}
 				} else {
 					responseText = "Formato de Fecha Incorrecto";
 					exchange.getResponseHeaders().add("Content-type", "application/json");
 					exchange.sendResponseHeaders(400, responseText.getBytes().length);
 				}
 			} else {
 				
 				exchange.sendResponseHeaders(405, -1);
 			}
 			OutputStream output = exchange.getResponseBody();
 			Instant finalDeEjecucion = Instant.now();//crea un objeto de tipo instant con fecha final de ejecucion

 			LOGGER.info("LearningJava - Cerrando recursos ...");
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
			LOGGER.info("Tiempo de respuesta: ".concat(total));
			output.write(responseText.getBytes());
 			output.flush();
 			output.close();
 			exchange.close();
 		}));
 		
 		server.createContext("/api/getStreams", (exchange -> {
            LOGGER.info(msgProcPeticion);
            Instant inicioDeEjecucion = Instant.now();
            BankAccountService bankAccountService = new BankAccountServiceImpl();
            String responseText = "";
            if ("GET".equals(exchange.getRequestMethod())) {
                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");

                List<String> lista = Arrays.asList("Martinez", "Solano", "Lopez", "Martinez", "Lopez", "Lopez", "Lopez");

                List<String> distinctElementos = lista.stream().distinct().collect(Collectors.toList());
                LOGGER.info(distinctElementos.get(0));

                List<String> filtroSupremo = lista.stream().filter(palabra -> palabra.equals("Lopez")).collect(Collectors.toList());

                JSONObject json = new JSONObject(distinctElementos);

                responseText = "Distintos: " + distinctElementos.get(0) + "-" + distinctElementos.get(1) +
                        "-" + distinctElementos.get(2) + "-" + distinctElementos.get(3) + " || Filtrado: " + filtroSupremo.get(0);

                exchange.getResponseHeaders().add("Content-type", "application/json");
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            OutputStream output = exchange.getResponseBody();
            Instant finalDeEjecucion = Instant.now();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            LOGGER.info("Tiempo de respuesta: ".concat(total));
            output.write(responseText.getBytes());
            output.flush();
            output.close();
            exchange.close();
        }));

 	// Consultar información de todas las cuentas
 				server.createContext("/api/getAccounts", (exchange -> {
 				LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
 				BankAccountService bankAccountService = new BankAccountServiceImpl();

 				String responseText = "";
 				
 				if ("GET".equals(exchange.getRequestMethod())) {
 					LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
 					List<BankAccountModel> accounts = bankAccountService.getAccounts();
 					JSONArray json = new JSONArray(accounts);
 					responseText = json.toString();
 					exchange.getResponseHeaders().add("Content-type", "application/json");
 					exchange.sendResponseHeaders(200, responseText.getBytes().length);
 				} else {
 					
 					exchange.sendResponseHeaders(405, -1);
 				}
 				OutputStream output = exchange.getResponseBody();
 
 				LOGGER.info("LearningJava - Cerrando recursos ...");
 				output.write(responseText.getBytes());
 				output.flush();
 				output.close();
 				exchange.close();
 			}));

 	        // Crear usuarios
 	        server.createContext("/api/createUsers", (exchange -> {
 	            LOGGER.info("LearningJava - Inicia procesamiento de peticion createUsers...");
 	         
 	            exchange.getRequestBody();
 	            
 	            if ("POST".equals(exchange.getRequestMethod())) {
 	                LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST");

 	                StringBuilder text = new StringBuilder();
 	                
 	                try (Scanner scanner = new Scanner(exchange.getRequestBody())) {
 	                	
 	                    while(scanner.hasNext()) {
 	                    	String tString= scanner.next();
 	                    	System.out.println("linea----> "+tString);
 	                        text.append(tString);
 	                    }
 	                    
 	                } catch (Exception e) {
 	                   LOGGER.severe(e.getMessage());
 	                  throw new ExceptionGenerica("Fallo al obtener el request del Servicedy");
 	              }
 	                
 	                textThread = text.toString();

 	                LOGGER.info(textThread);
 	                LearningJava thread = new LearningJava();
 	                thread.start();//iniciamos el hilo

 	                while(thread.isAlive());//esperamos a que termine el hilo

 	                exchange.getResponseHeaders().set("contentType", "application/json; charset=UTF-8");
 	                exchange.sendResponseHeaders(200, responseTextThread.getBytes().length);
 	            } else {
 	               
 	                exchange.sendResponseHeaders(405, -1);
 	            }
 	            OutputStream output = exchange.getResponseBody();
 	            

 	            LOGGER.info("LearningJava - Cerrando recursos ...");
 	            output.write(responseTextThread.getBytes());
 	            output.flush();
 	            output.close();
 	            exchange.close();
 	        }));
 	        
 	       // Consultar todas las cuentas y agruparlas por su tipo utilizando Programación Funcional
 	       server.createContext("/api/getAccountsGroupByType", (exchange -> {
 	          LOGGER.info(msgProcPeticion);
 	          Instant inicioDeEjecucion = Instant.now();
 	          BankAccountService bankAccountService = new BankAccountServiceImpl();
 	          String responseText = "";
 	        
 	          
 	          if ("GET".equals(exchange.getRequestMethod())) {
 	              LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
 	              List<BankAccountModel> accounts = bankAccountService.getAccounts();
 	              
 	           
 	              Map<String, List<BankAccountModel>> groupedAccounts;
 	              Function<BankAccountModel, String> groupFunction = (account) -> account.getAccountType().toString();
 	              groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

 	              JSONObject json = new JSONObject(groupedAccounts);
 	              responseText = json.toString();
 	              exchange.getResponseHeaders().add("Content-type", "application/json");
 	              exchange.sendResponseHeaders(200, responseText.getBytes().length);
 	          } else {
 	             
 	              exchange.sendResponseHeaders(405, -1);
 	          }
 	          OutputStream output = exchange.getResponseBody();
 	          Instant finalDeEjecucion = Instant.now();

 	          LOGGER.info("LearningJava - Cerrando recursos ...");
 	          String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
 	          LOGGER.info("Tiempo de respuesta: ".concat(total));
 	          output.write(responseText.getBytes());
 	          output.flush();
 	          output.close();
 	          exchange.close();
 	      }));
 	        
 		
 		
         server.setExecutor(null); 
         server.start();
         LOGGER.info("LearningJava - Server started on port 8080");
     
     }//lave main*/
private static ResponseModel login(String User, String password) {
	UserService userService = new UserServiceImpl();
	return userService.login(User, password);
}

private static ResponseModel createUser(String User, String password) {
	UserService userService = new UserServiceImpl();
	return userService.createUser(User, password);
}

public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
    String query = uri.getQuery();
    String[] pairs = query.split("&");
    for (String pair : pairs) {
        int idx = pair.indexOf("=");
        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
    }
    
    return query_pairs;
}

private static BankAccountModel getAccountDetails(String user,String lastUsage) {
	BankAccountService bankAccountService = new BankAccountServiceImpl();
	return bankAccountService.getAccountDetails(user, lastUsage);
}


@Override
public void run(){
    try {
        crearUsuarios();
    } catch (Exception e) {
        LOGGER.severe(e.getMessage());
        throw new ExceptionGenerica(e.getMessage());
    }
}


private void crearUsuarios() {
    try {
        String user = "user";
        String pass = "password";
        JSONArray jsonArray = new JSONArray(textThread);
        JSONObject userJson;

        ResponseModel response = null;

        LOGGER.info("jsonArray.length(): " + jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++) {
            userJson = new JSONObject(jsonArray.get(i).toString());
            response = createUser(userJson.getString(user), userJson.getString(pass));
            responseTextThread = new JSONObject(response).toString();
            LOGGER.info("Usuario " + (i+1) + ": " + responseTextThread);
            Thread.sleep(1000);
        }
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
}


@Deprecated()//since = "Anotaciones update"
private void createUsers() {
	
	  try {
	        String user = "user";
	        String pass = "password";
	        JSONArray jsonArray = new JSONArray(textThread);
	        JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
	        JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
	        JSONObject user3 = new JSONObject(jsonArray.get(2).toString());

	        // Creamos usuario 1
	        response = createUser(user1.getString(user), user1.getString(pass));
	        responseTextThread = new JSONObject(response).toString();
	        LOGGER.info("Usuario 1: " + responseTextThread);
	        Thread.sleep(1000);

	        // Creamos usuario 2
	        response = createUser(user2.getString(user), user2.getString(pass));
	        responseTextThread = new JSONObject(response).toString();
	        LOGGER.info("Usuario 2: " + responseTextThread);
	        Thread.sleep(1000);

	        // Creamos usuario 3
	        response = createUser(user3.getString(user), user3.getString(pass));
	        responseTextThread = new JSONObject(response).toString();
	        LOGGER.info("Usuario 3: " + responseTextThread);
	    } catch (InterruptedException e) {
	    	 LOGGER.severe(e.getMessage());
	         throw new ExceptionGenerica(e.getMessage());
	    }
	
}
}



