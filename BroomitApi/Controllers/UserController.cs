using BroomitApi.Models;
using BroomitApi.Services;
using BroomitModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;

namespace BroomitApi2.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase
{
    private readonly UserService _userService;
    
    public UserController(UserService userService) => 
        _userService = userService;
    
    [HttpGet]
    public async Task<List<User>> GetUsers() => await _userService.GetUsersAsync();
    
    [HttpGet("{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public async Task<ActionResult<User>> GetUser([FromBody] Guid id)
    {
        var result = await _userService.GetUserAsync(id.ToString());
        if (result is null)
            return NotFound();
        return result;
    }
    
    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<User>> CreateUser(
        String userName,
        String password,
         string email)
    {
        var result = await _userService.CreateUserAsync(userName, password, email);
        return CreatedAtAction(nameof(GetUser), new { id = result.Id }, result);
    }

    [HttpPut("{id}")]
    [ProducesResponseType(StatusCodes.Status204NoContent)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<IActionResult> UpdateUser(string id, User user)
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
        var result = await _userService.RemoveUserAsync(id);
        if (result is false)
            return NotFound();
        return NoContent();
    }
}