package com.hardnets.coop.controller;

import java.util.stream.Stream;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.hardnets.coop.model.dto.views.ViewSerializer;

@RestControllerAdvice
public class ControllerAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// Soporta cualquier método que retorne un objeto y tenga la anotación
		// @ViewSerializer
		return returnType.hasMethodAnnotation(ViewSerializer.class);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		ViewSerializer viewSerializer = returnType.getMethodAnnotation(ViewSerializer.class);
		Stream<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().stream();

		Class<?>[] views = viewSerializer.value();

		Class<?> selectedView = null;

		if (roles.anyMatch(a -> a.getAuthority().contains("KAL_EL"))) {
			selectedView = views[0];

		} else if (roles.anyMatch(a -> a.getAuthority().equals("ROLE_GESTOR_APR"))) {
			// Asumiendo que AppViews.Internal.class es la intermedia
			selectedView = views[1];
		} else {
			// Asumiendo que AppViews.Public.class es la vista básica
			// (Si tu lista de vistas es más compleja, esta lógica debe ser más robusta)
			// Para un caso simple: seleccionamos la vista más básica o la primera que no
			// requiera un rol.
		}

		if (selectedView != null) {
			MappingJacksonValue container = new MappingJacksonValue(body);
			container.setSerializationView(selectedView);
			return container;
		}

		return body;
	}
}
