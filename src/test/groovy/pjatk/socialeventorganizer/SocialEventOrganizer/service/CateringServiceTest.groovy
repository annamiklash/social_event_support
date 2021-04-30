package pjatk.socialeventorganizer.SocialEventOrganizer.service

import com.google.common.collect.ImmutableList
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.CateringMapper
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Catering
import pjatk.socialeventorganizer.SocialEventOrganizer.model.exception.IllegalArgumentException
import pjatk.socialeventorganizer.SocialEventOrganizer.model.exception.NotFoundException
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.CateringRequest
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.CateringRepository
import spock.lang.Specification
import spock.lang.Subject

class CateringServiceTest extends Specification {

    @Subject
    CateringService service

    CateringRepository repository

    CateringMapper mapper


    def setup() {
        repository = Mock()
        mapper = Mock()

        service = new CateringService(repository, mapper)
    }

    def "findAll positive test scenario"() {
        given:
        def catering = Catering.builder()
                .id(1)
                .name('Name')
                .city('Warsaw')
                .build()

        def cateringList = Arrays.asList(catering)

        def target = ImmutableList.copyOf(cateringList)

        when:
        def result = service.findAll()

        then:
        result == target

        1 * repository.findAll() >> target
    }

    def "findByCity positive test scenario"() {
        given:
        def catering = Catering.builder()
                .id(1)
                .name('Name')
                .city('Warsaw')
                .build()

        def city = 'Warsaw'
        def cateringList = Arrays.asList(catering)

        def target = ImmutableList.copyOf(cateringList)

        when:
        def result = service.findByCity(city)

        then:
        result == target

        1 * repository.findByCityContaining(city) >> target
    }

    def "findByCity negative test scenario"() {
        given:
        def city = 'Nope'

        when:
        service.findByCity(city)

        then:
        thrown(NotFoundException)
        1 * repository.findByCityContaining(city) >> { throw new NotFoundException() }
    }

    def "findByName positive test scenario"() {
        given:
        def catering = Catering.builder()
                .id(1)
                .name('Name')
                .city('Warsaw')
                .build()

        def  'Name'

        def cateringList = Arrays.asList(catering)

        def target = ImmutableList.copyOf(cateringList)

        when:
        def result = service.findByName(name)

        then:
        result == target

        1 * repository.findByNameContaining(name) >> target
    }

    def "findByName negative test scenario"() {
        given:
        def  'Nope'

        when:
        service.findByName(name)

        then:
        thrown(NotFoundException)
        1 * repository.findByNameContaining(name) >> { throw new NotFoundException() }
    }

    def "addNewCatering positive test scenario"() {
        given:
        def cateringRequest = CateringRequest.builder()
                .name('Name')
                .city('City')
                .build();

        def catering = Catering.builder()
                .name('Name')
                .city('City')
                .build();

        when:
        service.addNewCatering(cateringRequest)

        then:
        1 * mapper.updateMapToDTO(cateringRequest) >> catering
        1 * repository.save(catering)

    }

    def "updateCatering positive test scenario"() {
        given:
        def cateringRequest = CateringRequest.builder()
                .name('Name')
                .city('City')
                .build();

        def catering = Catering.builder()
                .id(1)
                .name('Name')
                .city('City')
                .build();

        when:
        service.updateCatering(1, cateringRequest)

        then:
        1 * repository.existsById(1) >> true
        1 * mapper.updateMapToDTO(cateringRequest, 1) >> catering
        1 * repository.save(catering)
    }

    def "updateCatering negative test scenario"() {
        given:
        def id = 10000

        def cateringRequest = CateringRequest.builder()
                .name('Name')
                .city('City')
                .build();

        def catering = Catering.builder()
                .id(id)
                .name('Name')
                .city('City')
                .build();

        when:
        service.updateCatering(id, cateringRequest)

        then:
        1 * repository.existsById(id) >> false
        thrown(IllegalArgumentException)
    }

    def "deleteCatering positive test scenario"() {
        given:
        def id = 1;

        when:
        service.deleteCatering(id)

        then:
        1 * repository.existsById(id) >> true
        1 * repository.deleteById(id)
    }

    def "deleteCatering negative test scenario"() {
        given:
        def id = 100000;

        when:
        service.deleteCatering(id)

        then:
        1 * repository.existsById(id) >> false
        thrown(IllegalArgumentException)
    }

}
