using MongoDB.Driver;
using BroomitApi.Models;
using User = BroomitModels.User;
using Microsoft.Extensions.Options;

namespace BroomitApi.Services;

public class UserService
{
    private readonly IMongoCollection<User> _usersCollection;

    public UserService(IOptions<BroomitDatabaseSettings> broomitDbSettings)
    {
        MongoClient mongoClient = new(broomitDbSettings.Value.ConnectionString);
        IMongoDatabase database = mongoClient.GetDatabase(broomitDbSettings.Value.DatabaseName);

        _usersCollection = database.GetCollection<User>(broomitDbSettings.Value.UsersCollectionName);
    }

    public async Task<List<User>> GetUsersAsync()
    {
        return await _usersCollection.Find(user => true).ToListAsync();
    }

    public async Task<string?>? LoginAsync(LoginRequest login)
    {
        User? user = await _usersCollection.Find(user => user.Username == login.Username).FirstOrDefaultAsync();
        if (user == null) return null;

        if (user.Password == login.Password)
            return user.Id;

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

    public async Task UpdateUserAsync(string id, User userIn)
    {
        await _usersCollection.ReplaceOneAsync(user => user.Id == id, userIn);
    }

    public async Task<bool> RemoveUserAsync(string id)
    {
        DeleteResult result = await _usersCollection.DeleteOneAsync(user => user.Id == id);
        return result.DeletedCount == 1;
    }
}