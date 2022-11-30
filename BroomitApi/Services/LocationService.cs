using MongoDB.Driver;
using BroomitApi.Models;
using Microsoft.Extensions.Options;
using Location = BroomitModels.Location;
using Task = System.Threading.Tasks.Task;

namespace BroomitApi.Services;

public class LocationService
{
    private readonly IMongoCollection<Location> _locationsCollection;

    public LocationService(IOptions<BroomitDatabaseSettings> broomitDbSettings)
    {
        MongoClient mongoClient = new(broomitDbSettings.Value.ConnectionString);
        IMongoDatabase database = mongoClient.GetDatabase(broomitDbSettings.Value.DatabaseName);

        _locationsCollection = database.GetCollection<Location>(broomitDbSettings.Value.LocationsCollectionName);
    }

    public async Task<List<Location>> GetLocationsAsync()
    {
        return await _locationsCollection.Find(location => true).ToListAsync();
    }

    public async Task<Location>? GetLocationAsync(string id)
    {
        return await _locationsCollection.Find(location => location.Id == id).FirstOrDefaultAsync();
    }

    public async Task<Location> CreateLocationAsync(Location location)
    {
        // TODO: geolocation (?)
        await _locationsCollection.InsertOneAsync(location);
        return location;
    }

    public async Task UpdateLocationAsync(string id, Location locationIn)
    {
        // TODO: geolocation (?)
        await _locationsCollection.ReplaceOneAsync(location => location.Id == id, locationIn);
    }

    public async Task<bool> RemoveLocationAsync(string id)
    {
        DeleteResult result = await _locationsCollection.DeleteOneAsync(location => location.Id == id);
        return result.DeletedCount == 1;
    }
}