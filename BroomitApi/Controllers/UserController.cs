using BroomitApi.Models;
using BroomitApi.Services;
using BroomitModels;
using Microsoft.AspNetCore.Mvc;

namespace BroomitApi.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase
{
    private readonly UserService _userService;
    
    public UserController(UserService userService) => 
        _userService = userService;
    
    [HttpGet("{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<ActionResult<User>> GetUser([FromRoute] string id)
    {
        var result = await _userService.GetUserAsync(id);
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
        var result = await _userService.CreateUserAsync(user);
        return result.Id;
    }

    [HttpPut("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<IActionResult> UpdateUser(string id, User user)
    {
        if (id != user.Id)
            return BadRequest();
        await _userService.UpdateUserAsync(id, user);
        return Ok();
    }
    
    [HttpDelete("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<IActionResult> DeleteUser(string id)
    {
        var result = await _userService.RemoveUserAsync(id);
        if (result is false)
            return NotFound();
        return NoContent();
    }
}