using BroomitApi.Models;
using BroomitApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace BroomitApi2.Controllers;

[ApiController]
[Route("[controller]")]
public class LoginController : Controller
{
    private readonly UserService _userService;

    public LoginController(UserService userService) =>
        _userService = userService;

    [HttpPost]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<Guid>> Login([FromBody] LoginRequest loginRequest)
    {
        if (loginRequest.Username?.Length == 0 || loginRequest.Password?.Length == 0)
            return BadRequest();

        var guid = await _userService.LoginAsync(loginRequest);
        if (guid is null)
            return BadRequest();
        return guid;
    }
}
