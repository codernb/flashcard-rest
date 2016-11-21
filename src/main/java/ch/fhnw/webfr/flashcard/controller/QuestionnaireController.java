package ch.fhnw.webfr.flashcard.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

@RestController
@RequestMapping("/questionnaires")
public class QuestionnaireController {

	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@GetMapping
	public @ResponseBody List<Questionnaire> findAll() {
		return questionnaireRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Questionnaire> create(@Valid @RequestBody Questionnaire questionnaire,
			BindingResult bindingResult) {
		return bindingResult.hasErrors() ? new ResponseEntity<Questionnaire>(HttpStatus.BAD_REQUEST)
				: new ResponseEntity<Questionnaire>(questionnaireRepository.save(questionnaire), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Questionnaire> update(@Valid @RequestBody Questionnaire questionnaire,
			BindingResult bindingResult) {
		return bindingResult.hasErrors() ? new ResponseEntity<Questionnaire>(HttpStatus.BAD_REQUEST)
				: new ResponseEntity<Questionnaire>(questionnaireRepository.save(questionnaire), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		questionnaireRepository.delete(id);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

}
