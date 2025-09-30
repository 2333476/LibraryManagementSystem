//package com.example.apigatewayservice.presentationlayer;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//
//@RestController
//@RequestMapping("/db")
//public class DatabaseGuiController {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @RequestMapping("/phpmyadmin-customers/**")
//    public ResponseEntity<Resource> proxyPhpMyAdminCustomers(HttpMethod method,
//                                                             HttpServletRequest request,
//                                                             @RequestBody(required = false) byte[] body) {
//        return forward("http://phpmyadmin-customers", request, method, body);
//    }
//
//    @RequestMapping("/phpmyadmin-employees/**")
//    public ResponseEntity<Resource> proxyPhpMyAdminEmployees(HttpMethod method,
//                                                             HttpServletRequest request,
//                                                             @RequestBody(required = false) byte[] body) {
//        return forward("http://phpmyadmin-employees", request, method, body);
//    }
//
//    @RequestMapping("/pgadmin-book/**")
//    public ResponseEntity<Resource> proxyPgAdminBook(HttpMethod method,
//                                                     HttpServletRequest request,
//                                                     @RequestBody(required = false) byte[] body) {
//        return forward("http://pgadmin-book", request, method, body);
//    }
//
//    @RequestMapping("/mongo-express/**")
//    public ResponseEntity<Resource> proxyMongoExpress(HttpMethod method,
//                                                      HttpServletRequest request,
//                                                      @RequestBody(required = false) byte[] body) {
//        return forward("http://mongo-express:8081", request, method, body);
//    }
//
//    private ResponseEntity<Resource> forward(String baseUrl,
//                                             HttpServletRequest request,
//                                             HttpMethod method,
//                                             byte[] body) {
//        String fullPath = request.getRequestURI(); // e.g. /db/phpmyadmin-customers/themes/style.css
//        String toolPrefix = fullPath.split("/")[2]; // phpmyadmin-customers
//        String path = fullPath.replaceFirst("/db/" + toolPrefix, "");
//        String url = baseUrl + path;
//
//        HttpHeaders headers = new HttpHeaders();
//        Collections.list(request.getHeaderNames()).forEach(header ->
//                headers.set(header, request.getHeader(header))
//        );
//
//        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<byte[]> response = restTemplate.exchange(
//                url, method, entity, byte[].class);
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        response.getHeaders().forEach((key, value) -> responseHeaders.put(key, value));
//
//        return new ResponseEntity<>(
//                new ByteArrayResource(response.getBody()),
//                responseHeaders,
//                response.getStatusCode()
//        );
//    }
//}
