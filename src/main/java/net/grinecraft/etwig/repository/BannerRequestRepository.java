package net.grinecraft.etwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.BannerRequest;


public interface BannerRequestRepository extends JpaRepository <BannerRequest, Long>  {

	long countByEventId(long eventId);
}
