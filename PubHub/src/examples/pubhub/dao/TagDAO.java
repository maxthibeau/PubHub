package examples.pubhub.dao;
import java.util.List;
import examples.pubhub.model.Tag;

/**
 * Interface for our Data Access Object to handle database queries related to Tags.
 */
public interface TagDAO {

	public List<Tag> getAllTagsForBook(String isbn13);
	public boolean setAllTagsForBook(String isbn13, String tags);
}
