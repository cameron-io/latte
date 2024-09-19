package io.netstacker.latte.application.specifications;

import org.springframework.data.jpa.domain.Specification;

import io.netstacker.latte.domain.models.Profile;
import io.netstacker.latte.domain.models.Profile_;

public class ProfileSpecifications {
    public static Specification<Profile> ProfileByAccountId(Long accountId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(
                root.get(Profile_.ACCOUNT),
                accountId);
    }
}
