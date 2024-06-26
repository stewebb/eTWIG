package net.etwig.webapp.repository;

import net.etwig.webapp.model.BannerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GraphicsRequestRepository extends JpaRepository <BannerRequest, Long>, JpaSpecificationExecutor<BannerRequest> {

}
