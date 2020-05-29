package examples.pubhub.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

public class TagDAOImpl implements TagDAO {

	Connection connection = null;	// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	public List<Tag> getAllTagsForBook(String isbn13) {
		
		List<Tag> tags = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM book_tags WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, isbn13);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Tag tag = new Tag(rs.getString("tag_name"));
				tags.add(tag);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return tags;
	}
	
	public boolean setAllTagsForBook(String isbn13, String tags) {
		
		try {
			connection = DAOUtilities.getConnection();
			// clear out old tags
			String sql = "DELETE FROM book_tags WHERE isbn_13=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, isbn13);
			stmt.executeUpdate();			
			String[] tags_split = tags.split(" ");
			for (String tag: tags_split) {
				sql = "INSERT INTO book_tags (isbn_13, tag_name) VALUES (?, ?)";
				stmt = connection.prepareStatement(sql);
				stmt.setString(1, isbn13);
				stmt.setString(2, tag);
				System.out.println(stmt);
				if (stmt.executeUpdate() == 0) {
					return false;
				}
			}
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	/*------------------------------------------------------------------------------------------------*/

	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
