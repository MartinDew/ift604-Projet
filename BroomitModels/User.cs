namespace BroomitModels;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

public class User
{
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string? Id { get; set; }
    [BsonElement("Name")]
    public string Username { get; set; }
    public string Password { get; set; }
    public string Email { get; set; }

    public List<Location> Locations { get; set; }

    // Constructor
    public User(string username, string password, string email)
    {
        Username = username;
        Password = password;
        Email = email;
    }
}