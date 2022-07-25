package ru.myrating.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrating.application.domain.Authority_;
import ru.myrating.application.domain.User;
import ru.myrating.application.domain.UserProfile_;
import ru.myrating.application.domain.User_;
import ru.myrating.application.repository.UserRepository;
import ru.myrating.application.service.dto.UserCriteria;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.StringFilter;

import java.util.Optional;

import static java.util.List.of;
import static javax.persistence.criteria.JoinType.INNER;
import static org.springframework.data.jpa.domain.Specification.where;
import static ru.myrating.application.security.AuthoritiesConstants.USER;

@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {
    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    private final UserRepository userRepository;

    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllManagedUsers(UserCriteria criteria, Pageable pageable) {
        log.debug("find by criteria : {}, page: {}", criteria, pageable);
        StringFilter authoritiesFilter = new StringFilter();
        authoritiesFilter.setIn(of(USER));
        criteria.setAuthorities(authoritiesFilter);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, pageable);
    }

    public Optional<User> findUserByReferenceLink(UserCriteria criteria) {
        return userRepository.findOne(createSpecification(criteria));
    }

    protected Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = where(null);
        if (criteria != null) {
            if (criteria.getAuthorities() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorities(),
                        root -> root.join(User_.authorities, INNER).get(Authority_.name)));
            }
            if (criteria.getPartnerName() != null) {
                specification = specification.and(buildSpecification(criteria.getPartnerName(),
                        root -> root.join(User_.profile, INNER).get(UserProfile_.partnerName)));
            }
            if (criteria.getReferenceLink() != null) {
                specification = specification.and(buildSpecification(criteria.getReferenceLink(),
                        root -> root.join(User_.profile, INNER).get(UserProfile_.referenceLink)));
            }
        }
        return specification;
    }
}
