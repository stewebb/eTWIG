package net.etwig.webapp.controller.api;

import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/portfolio/")
public class PortfolioAPIController {

    @Autowired
    PortfolioService portfolioService;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> eventInfo) {

        // TODO Add an Portfolio
        return null;
    }

    @PostMapping("/edit")
    public Map<String, Object> edit(@RequestBody Map<String, Object> eventInfo) {

        // TODO Edit an portfolio
        return null;
    }

    /**
     * Handles a GET request to view a specific {@link Portfolio} by its identifier.
     * <p>
     * This method processes the incoming POST request to retrieve a portfolio using the {@code portfolioId}
     * provided as a request parameter. The actual retrieval of the portfolio is delegated to the {@code portfolioService}.
     * If no portfolio with the specified ID exists, the method will return null.
     *
     * @param portfolioId The unique identifier of the portfolio to be viewed.
     * @return The {@link Portfolio} instance associated with the given ID, or null if no such portfolio exists.
     * @location /api/portfolio/view
     * @permission All logged in users.
     */

    @GetMapping("/view")
    public Portfolio view(@RequestParam Long portfolioId) {
        return portfolioService.findById(portfolioId);
    }

    @PostMapping("/remove")
    // TODO REMOVE an Portfolio
    public Map<String, Object> remove(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

    /**
     * Handles HTTP GET requests to retrieve a paginated list of portfolios, optionally filtered by whether they have a separated
     * calendar, and sorted based on the specified column and direction.
     * <p>
     * This endpoint supports dynamic pagination, sorting, and filtering to accommodate the front-end table display requirements
     * (e.g., DataTables). It constructs a {@link PageRequest} using the provided start index, page size, sort column, and sort
     * direction. The results, along with additional pagination information such as total records, are encapsulated in a JSON object.
     * </p>
     *
     * @param separatedCalendar Optional filter to only include portfolios that have a separated calendar ({@code true}) or not
     *                          ({@code false}). If not specified, all portfolios are included.
     * @param start The zero-based index of the first record to be retrieved, used for pagination.
     * @param length The maximum number of records to return, used for pagination.
     * @param draw An integer sent by the client used to ensure responses are in sync with requests in asynchronous environments.
     * @param sortColumn The name of the property/column to sort by.
     * @param sortDirection The direction of the sort, can be 'asc' for ascending or 'desc' for descending.
     * @return A {@link ResponseEntity} containing a {@link Map} structured as a JSON object with pagination and list data suitable
     *         for client-side rendering, such as in a DataTables setup. Includes total record count, filtered count, and actual data.
     * @location /api/portfolio/list
     * @permission All logged in users.
     */

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(name = "separatedCalendar", required = false) Boolean separatedCalendar,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam("draw") int draw,
            @RequestParam("sortColumn") String sortColumn,
            @RequestParam("sortDirection") String sortDirection
    ) {

        Sort.Direction dir = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageable = PageRequest.of(start / length, length, Sort.by(dir, sortColumn));

        // Get data as pages
        Page<Portfolio> page = portfolioService.findByCriteria(separatedCalendar, pageable);

        Map<String, Object> json = new HashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());
        return ResponseEntity.ok(json);
    }
}
