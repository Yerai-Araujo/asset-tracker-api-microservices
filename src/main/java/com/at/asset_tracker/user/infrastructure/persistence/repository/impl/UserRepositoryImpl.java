package com.at.asset_tracker.user.infrastructure.persistence.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.at.asset_tracker.portfolio.infrastructure.persistence.entity.PortfolioEntity;
import com.at.asset_tracker.portfolio.infrastructure.persistence.repository.PortfolioJpaRepository;
import com.at.asset_tracker.user.domain.model.User;
import com.at.asset_tracker.user.domain.repository.UserRepository;
import com.at.asset_tracker.user.infrastructure.persistence.entity.UserEntity;
import com.at.asset_tracker.user.infrastructure.persistence.repository.UserJpaRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final PortfolioJpaRepository portfolioJpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository, PortfolioJpaRepository portfolioJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.portfolioJpaRepository = portfolioJpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.id());
        entity.setEmail(user.email());
        entity.setName(user.name());

        if (user.portfolioId() != null) {
            PortfolioEntity ref = portfolioJpaRepository.getReferenceById(user.portfolioId());
            entity.setPortfolio(ref);
        }

        return entity;
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getPortfolio() != null ? entity.getPortfolio().getId() : null
        );
    }
}
