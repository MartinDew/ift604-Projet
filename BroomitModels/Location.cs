using System.Text.Json.Serialization;

namespace BroomitModels;

public class Location
{
    public class Geolocation
    {
        [JsonPropertyName("latitude")]
        public double Latitude { get; set; }

        [JsonPropertyName("longitude")]
        public double Longitude { get; set; }
    }

    //[JsonPropertyName("id")]
    //public string? Id { get; set; }

    [JsonPropertyName("name")]
    public string? Name { get; set; }

    [JsonPropertyName("address")]
    public string? Address { get; set; }

    [JsonPropertyName("notes")]
    public string Notes { get; set; } = "";

    [JsonPropertyName("owner_phone_number")]
    public string OwnerPhoneNumber { get; set; } = "";

    [JsonPropertyName("tasks")]
    public List<Task> Tasks { get; set; } = new List<Task>();

    [JsonPropertyName("position")]
    public Geolocation Position { get; set; }
}
