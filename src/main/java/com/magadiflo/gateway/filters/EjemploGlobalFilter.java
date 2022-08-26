package com.magadiflo.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilter implements GlobalFilter {
	
	private final static Logger LOG = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	//¡Atención!, usa programación REACTIVA
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		LOG.info("Ejecutando filtro pre");
		//Todo lo que está sobre el return, es el pre. Y todo lo que está dentro de 
		//la exprsión lambda es el post
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			LOG.info("Ejecutando filtro post");
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		})); //Que continúe con la ejecución de la cadena de filtros
	}	
	

}
