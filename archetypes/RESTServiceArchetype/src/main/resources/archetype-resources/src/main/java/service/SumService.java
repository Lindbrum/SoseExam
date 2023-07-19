package ${package}.service;

import ${package}.exception.TemplateNotFoundException;

public interface SumService {
	String getTemplate() throws TemplateNotFoundException;
}
