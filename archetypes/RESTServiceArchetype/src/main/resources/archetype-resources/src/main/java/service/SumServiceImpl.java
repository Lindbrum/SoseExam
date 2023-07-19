package ${package}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${package}.dao.SumTemplateRepository;
import ${package}.exception.TemplateNotFoundException;

@Service
public class SumServiceImpl implements SumService {

	private final SumTemplateRepository sumRepository;
	
	@Autowired
	public SumServiceImpl(SumTemplateRepository sumRepository) {
		this.sumRepository = sumRepository;
	}
	
	@Override
	public String getTemplate() throws TemplateNotFoundException {
		
		return sumRepository.findById(1L).orElseThrow(() -> new TemplateNotFoundException("Template missing from datasource.")).getTemplate();
	}

}
