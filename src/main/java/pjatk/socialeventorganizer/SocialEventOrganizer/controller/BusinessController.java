package pjatk.socialeventorganizer.SocialEventOrganizer.controller;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Business;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.BusinessRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.BusinessResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.service.BusinessService;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("api/business")
public class BusinessController {

    private final BusinessService service;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImmutableList<Business>> findAll() {
        log.info("GET ALL BUSINESSES");
        final ImmutableList<Business> all = service.findAll();
        log.info(String.valueOf(all.size()));
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BusinessResponse> addBusiness(@Valid @RequestBody BusinessRequest request) {
        final BusinessResponse response = service.addNewBusiness(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
