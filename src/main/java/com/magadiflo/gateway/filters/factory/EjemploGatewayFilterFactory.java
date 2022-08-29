package com.magadiflo.gateway.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Si queremos que el nombre de la clase sea detectado en automático como un
 * filtro, debemos agregarle el sufijo: <cualquier_nombre_>GatewayFilterFactory,
 * caso contrario se le podría dar cualquier otro nombre, pero se tendría que
 * hacer configuraciones en el application.properties. De todos modos en el
 * application.properties debemos configurarlo con el nombre: Ejemplo, sin el
 * sufijo
 * 
 * Creamos una clase de configuración anidada dentro de la misma clase. Esa
 * clase podríamos haberla creado en un paquete distinto, pero para evitar estar
 * creando lo hicimos aquí dentro
 *
 */
@Component
public class EjemploGatewayFilterFactory
		extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

	private static final Logger LOG = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	public EjemploGatewayFilterFactory() {
		super(Configuracion.class);
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		return (exchange, chain) -> {
			// pre
			LOG.info("Ejecutando PRE Gategay Filter Factory: {}", config.mensaje);

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
					// Importante el build(), para generar el objeto
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
				});
				LOG.info("Ejecutando POST Gategay Filter Factory: {}", config.mensaje);
			}));
		};
	}

	public static class Configuracion {

		private String mensaje;
		private String cookieValor;
		private String cookieNombre;

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getCookieValor() {
			return cookieValor;
		}

		public void setCookieValor(String cookieValor) {
			this.cookieValor = cookieValor;
		}

		public String getCookieNombre() {
			return cookieNombre;
		}

		public void setCookieNombre(String cookieNombre) {
			this.cookieNombre = cookieNombre;
		}

	}

}
