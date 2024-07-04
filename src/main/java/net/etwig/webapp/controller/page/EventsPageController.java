/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
 	* @license: MIT
 	* @author: Steven Webb [xiaoancloud@outlook.com]
 	* @website: https://etwig.grinecraft.net
 	* @function: The controller for add/edit/delete event.
 */

package net.etwig.webapp.controller.page;

import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.services.UserSessionService;
import net.etwig.webapp.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.etwig.webapp.services.PortfolioService;
import net.etwig.webapp.services.PropertyService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/events")
public class EventsPageController {

	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private Endpoints endpoints;

	/**
	 * Handles the HTTP GET request at the root of the 'events' module by redirecting to the main index page. This method serves as
	 * the entry point for the events section of the application, where it automatically redirects users to a centralized index page,
	 * facilitating easier navigation and consistent user experience across the platform. This redirection is essential for maintaining
	 * a structured flow within the application, especially for first-time users or general navigation purposes.
	 *
	 * @location /events/  (This assumes the controller is mapped to "/events")
	 * @permission Accessible only to logged in users, ensuring that the functionality is available for authenticated sessions only.
	 * @return The redirection path to the index page.
	 */

	@GetMapping("/")
	public String root(){
		return "redirect:" + endpoints.getEVENTS_CALENDAR();
	}

	/**
	 * Serves the event calendar page for the 'events' module, which displays a calendar with relevant event data. This method
	 * retrieves a list of portfolios via the portfolioService, and adds them to the model to be accessible within the view.
	 * This setup is integral for displaying portfolios that may be linked to specific events on the calendar. The calendar
	 * view provides a visual representation of event timelines and is designed to be interactive and user-friendly for logged-in users.
	 *
	 * @location /events/calendar.do
	 * @permission Accessible only to users who are logged in, ensuring that the calendar and its associated event data are
	 *             securely accessed by authenticated users.
	 * @param model The Spring Model object that is used to pass attributes to the view. It's utilized here to add portfolios
	 *              to the view, enhancing the functionality of the calendar page.
	 * @return The name of the view ('events/calendar') that renders the calendar.
	 */
	
	@GetMapping("/calendar.do")
	public String calendar(Model model){
		model.addAttribute(
				"portfolios",
				portfolioService.getPortfolioList(Pageable.unpaged()).getContent()
		);
		return "events/calendar";
	}

	/**
	 * Handles the creation or modification of an event. This controller method serves the add or edit event pages.
	 * It prepares necessary model attributes including user's current position, count of positions, all properties,
	 * and options related to a specific event.
	 *
	 * @param model The {@link Model} to which attributes are added to be used by the view.
	 * @param eventId The optional ID of the event to edit. If not provided, the method assumes a new event is being created.
	 * @return The path to the view which is responsible for rendering the event add/edit page.
	 * @location /events/edit.do - This endpoint is triggered for editing events.
	 * @permission All logged-in users are permitted to access this endpoint.
	 */
	
	@RequestMapping({"/add.do", "/edit.do"})
	public String edit(Model model, @RequestParam(required = false) Long eventId){

		CurrentUserPositionDTO position = userSessionService.validateSession().getPosition();
		model.addAttribute("myCurrentPosition", position.getMyCurrentPosition());
		model.addAttribute("myPositionCount", position.getMyPositions().size());

		model.addAttribute("allProperties", propertyService.findAll());
        model.addAttribute("allOptions", propertyService.getOptionsByEvent(eventId));
		return "events/edit";
	}
	// TODO Add a "view only" page, then set the permission of old pages to "event manager only"

	/**
	 * Displays the page for importing multiple events simultaneously via an Excel or ODS file.
	 * This method handles the GET request to the event import page, facilitating the bulk upload of event data.
	 *
	 * @location /events/import.do  The URL endpoint that triggers this method.
	 * @permission Required permission: ROLE_EVENTS. Only users with event management authority are allowed access.
	 * @return The path to the import events view, allowing the user to proceed with the file upload.
	 */

	@GetMapping("/import.do")
	@PreAuthorize("hasAuthority('ROLE_EVENTS')")
	public String importEvent(){
		return "events/import";
	}

	/**
	 * Retrieves the view name for displaying the list of events. This method is responsible for handling
	 * GET requests to the specified endpoint and simply directs the user to the page where all events are listed.
	 *
	 * <p>This method currently does not manipulate the model or session attributes directly but serves as a straightforward
	 * navigation point for viewing all events. It can be a point of integration for future enhancements such as
	 * filtering or sorting the list of events based on user preferences or permissions.</p>
	 *
	 * @return The view name "events/list" which corresponds to the events list page. This is used by the view resolver
	 *         to render the list of events to the user.
	 * @location /events/list.do - Accessed through a GET request to manage and view all the events.
	 * @permission All logged-in users have access to this endpoint, which is intended for a broad audience within the application.
	 */

	@GetMapping("/list.do")
	public String list(){
		return "events/list";
	}

	@GetMapping("/bannerRequest.do")
	public String bannerRequest(){
		return "events/bannerRequest";
	}
}
