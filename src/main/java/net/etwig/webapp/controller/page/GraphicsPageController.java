package net.etwig.webapp.controller.page;

import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.dto.graphics.BannerRequestDetailsDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsAPIForDetailsPageDTO;
import net.etwig.webapp.model.BannerRequest;
import net.etwig.webapp.services.BannerRequestService;
import net.etwig.webapp.services.EventGraphicsService;
import net.etwig.webapp.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@Secured({"hasAuthority(ROLE_GRAPHICS)"})
@RequestMapping("/graphics")
public class GraphicsPageController {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventGraphicsService eventGraphicsService;

	@Autowired
	private BannerRequestService bannerRequestService;

	/**
	 * Displays the event list page for graphics-related events.
	 * <p>
	 * This method handles the GET request to show a list of events. It returns the view name
	 * of the template used to render the event list, specifically tailored for users with graphics-related
	 * roles. The template is located under the 'graphics' directory.
	 * </p>
	 * <p>
	 * Security: Access to this method is restricted to users with the 'ROLE_GRAPHICS' authority, ensuring
	 * that only authorized personnel can view the graphics event list.
	 * </p>
	 *
	 * @return The path to the 'event_list' template within the 'graphics' directory.
	 * @location /graphics/eventList.do
	 * @permission Those who has graphic management permission.
	 */

	@PreAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@GetMapping("/eventList.do")
	public String eventList(){
		return "graphics/event_list";
	}

	// TODO **Copy** this page to /events, then display different contents based on user permission

	@PreAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@GetMapping("/eventGraphics.do")
	public String eventGraphics(Model model, @RequestParam Long eventId) throws Exception {
		EventDetailsDTO event = eventService.findById(eventId);
		if(event == null) {
			model.addAttribute("reason", "Event with id=" + eventId + " doesn't exist.");
			return "error_page";
		}

		// Disable pagination for results, but sort them by uploadedTime descending.
		Pageable pageable = Pageable.unpaged(Sort.by(Sort.Direction.DESC, "uploadedTime"));

		// Get graphics info
		Page<EventGraphicsAPIForDetailsPageDTO> banners = eventGraphicsService.findByCriteriaForDetails(eventId, true, pageable);
		Page<EventGraphicsAPIForDetailsPageDTO> twigComponents = eventGraphicsService.findByCriteriaForDetails(eventId, false, pageable);

		model.addAttribute("eventInfo", event);
		model.addAttribute("eventBanners", banners.getContent());
		model.addAttribute("twigComponents", twigComponents.getContent());
		return "graphics/event_view";
	}

	/**
	 * Handles the GET request for the approval list page.
	 * This method serves as the entry point for displaying the graphics approval list view.
	 * It simply routes the request to the appropriate template view which is responsible for rendering the approval list.
	 *
	 * @return a string indicating the path to the view template for the graphics approval list,
	 *         which is resolved by the framework's view resolver to render the actual HTML page.
	 * @location /graphics/approvalList.do
	 * @permission Those who has graphic management permission.
	 */

	@PreAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@GetMapping("/approvalList.do")
	public String approvalList() {
		return "graphics/approval_list";
	}

	/**
	 * Handles the GET request to retrieve the details of a specific banner request for approval.
	 * This method is responsible for fetching and displaying detailed information about a banner request and its associated event.
	 * <p>
	 * Upon receiving a request ID, it first retrieves the corresponding {@link BannerRequest} from the service layer.
	 * If the banner request does not exist, it redirects to an error page with an appropriate message.
	 * Otherwise, it proceeds to construct DTOs for both the banner request and the associated event, and adds these to the model.
	 * <p>
	 * This setup allows the view template to present detailed information about the banner request and event,
	 * aiding in the approval process.
	 *
	 * @param model the {@link Model} object to which attributes are added, enabling data to be passed to the view
	 * @param requestId the ID of the banner request to be retrieved and displayed; must not be {@code null}
	 * @return a string that indicates the path to the view template which is responsible for rendering the approval details page
	 *         or an error page if the specified banner request does not exist
	 * @location /graphics/approvalDetails.do
	 * @permission Those who has graphic management permission.
	 */

	@PreAuthorize("hasAuthority('ROLE_GRAPHICS')")
	@GetMapping("/approvalDetails.do")
	public String approvalDetails(Model model, @RequestParam @NonNull Long requestId) {

		// Get banner request details
		BannerRequest bannerRequest = bannerRequestService.findById(requestId);
		if (bannerRequest == null) {
			model.addAttribute("reason", "Banner request with id=" + requestId + " does not exist.");
			return "error_page";
		}
		BannerRequestDetailsDTO requestDetails = new BannerRequestDetailsDTO(bannerRequest);

		// Get relevant event information.
		EventDetailsDTO eventDetails = new EventDetailsDTO(bannerRequest.getEvent());

		model.addAttribute("requestInfo", requestDetails);
		model.addAttribute("eventInfo", eventDetails);
		return "graphics/approval_details";
	}
}
