using BroomitApi.Models;
using User = BroomitModels.User;
using Microsoft.Extensions.Options;
using MongoDB.Driver;

namespace BroomitApi.Services;

public class UserService
{
    private readonly IMongoCollection<User> _usersCollection;

    public UserService(IOptions<BroomitDatabaseSettings> broomitDbSettings)
    {
        var mongoClient = new MongoClient(broomitDbSettings.Value.ConnectionString);
        var database = mongoClient.GetDatabase(broomitDbSettings.Value.DatabaseName);

        _usersCollection = database.GetCollection<User>(broomitDbSettings.Value.UsersCollectionName);
    }

    public async Task<List<User>> GetUsersAsync()
    {
        return await _usersCollection.Find(user => true).ToListAsync();
    }

    public async Task<Guid?>? LoginAsync(LoginRequest login)
    {
        var user = await _usersCollection.Find(user => user.Username == login.Username).FirstOrDefaultAsync();
        if (user == null) return null;

        if (user.Password == login.Password)
            return new Guid(user.Id);

        return null;
    }

    public async Task<User>? GetUserAsync(string id)
    {
        return await _usersCollection.Find(user => user.Id == id).FirstOrDefaultAsync();
    }
    public async Task<User> CreateUserAsync(User user)
    {
        await _usersCollection.InsertOneAsync(user);
        return user;
    }

    // Update user with options
    public async Task UpdateUserAsync(string id, User userIn)
    {
        await _usersCollection.ReplaceOneAsync(user => user.Id == id, userIn);
    }

    // Just Remove user
    public async Task<bool> RemoveUserAsync(string id)
    {
        var r = await _usersCollection.DeleteOneAsync(user => user.Id == id);
        return r.DeletedCount == 1;
    }
}