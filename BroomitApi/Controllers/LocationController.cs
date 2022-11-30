using BroomitModels;
using BroomitApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace BroomitApi2.Controllers;

[ApiController]
[Route("[controller]")]
public class LocationController : Controller
{
    private readonly LocationService _locationService;

    public LocationController(LocationService locationService) => _locationService = locationService;

    [HttpGet("{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<ActionResult<Location>> GetLocation([FromRoute] string id)
    {
        Location? result = await _locationService.GetLocationAsync(id);
        if (result is null)
            return NotFound();
        return result;
    }

    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<string>> CreateLocation([FromBody] Location location)
    {
        if (location.Name?.Length == 0 || location.Address?.Length == 0 || location.Position.Equals(null))
            return BadRequest();

        // TODO: geolocation (?)

        Location? result = await _locationService.CreateLocationAsync(location);
        return result.Id;
    }

    [HttpPut("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<IActionResult> UpdateLocation([FromRoute] string id, [FromBody] Location location)
    {
        if (id != location.Id)
            return BadRequest();

        // TODO: geolocation (?)

        await _locationService.UpdateLocationAsync(id, location);
        return Ok();
    }

    [HttpDelete("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<IActionResult> DeleteLocation(string id)
    {
        bool result = await _locationService.RemoveLocationAsync(id);
        if (result is false)
            return NotFound();
        return NoContent();
    }
}

