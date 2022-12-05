using BroomitModels;
using BroomitApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace BroomitApi.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase
{
    private readonly UserService _userService;
    
    public UserController(UserService userService) => _userService = userService;
    
    // Get that returns a test
    [HttpGet]
    public ActionResult<string> Get() => Ok("Hello World!");
    
    [HttpGet("{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<ActionResult<User>> GetUser([FromRoute] string id)
    {
        User? result = await _userService.GetUserAsync(id);
        if (result is null)
            return NotFound();
        return result;
    }
    
    
    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<string>> CreateUser([FromBody] User user)
    {
        if (user.Username?.Length == 0 || user.Password?.Length == 0 || user.Email?.Length == 0)
            return BadRequest();

        user.Locations = new List<Location>();
        User? result = await _userService.CreateUserAsync(user);
        // return result?.Id ?? new HttpResponseMessage(System.Net.HttpStatusCode.NotFound).ToString();
        return result.Id;
    }

    [HttpPut("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<IActionResult> UpdateUser([FromRoute] string id, [FromBody] User user)
    {
        if (id != user.Id)
            return BadRequest();
        await _userService.UpdateUserAsync(id, user);
        return NoContent();
    }
    
    [HttpDelete("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<IActionResult> DeleteUser(string id)
    {
        bool result = await _userService.RemoveUserAsync(id);
        if (result is false)
            return NotFound();
        return NoContent();
    }
}