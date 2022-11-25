using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace BroomitModels;
public class Location
{
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string? Id { get; set; }
    public string Name { get; set; }
    public string Address { get; set; }
    public string Notes { get; set; } = "";
    public string OwnerPhoneNumber { get; set; } = "";
}
