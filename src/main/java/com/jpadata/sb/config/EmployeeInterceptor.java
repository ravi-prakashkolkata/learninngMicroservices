/*
 * package com.jpadata.sb.config;
 * 
 * import javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.http.HttpRequest; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.servlet.ModelAndView; import
 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 * 
 * @Component public class EmployeeInterceptor extends
 * HandlerInterceptorAdapter{
 * 
 * public boolean preHandle(HttpRequest req, HttpServletResponse res, Object
 * handler)throws Exception {
 * System.out.println("Pre Handle method is Calling"); return true; } public
 * void postHandle(HttpRequest req, HttpServletResponse res, Object handler,
 * ModelAndView mvc) throws Exception{
 * System.out.println("Post Handle method is Calling"); }
 * 
 * public void afterCompletion(HttpRequest req, HttpServletResponse res, Object
 * handler, Exception exc) {
 * System.out.println("complete Handle method is Calling"); } }
 */