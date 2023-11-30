package net.grinecraft.etwig.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.Model.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
	
    public List<Portfolio> findAll();
    public Optional<Portfolio> findById(long id);
}