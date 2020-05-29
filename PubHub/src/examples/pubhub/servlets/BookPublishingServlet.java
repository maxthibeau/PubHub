package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/*
 * This servlet will take you to the homepage for the Book Publishing module (level 100)
 */
@WebServlet("/BookPublishing")
public class BookPublishingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Grab the list of Books from the Database
		BookDAO dao = DAOUtilities.getBookDAO();
		TagDAO tagdao = DAOUtilities.getTagDAO();
		List<Book> bookList = dao.getAllBooks();
		List<String> tagList = new ArrayList<String>();
		for (Book book: bookList) {
			// this is lazy
			List<Tag> tags = tagdao.getAllTagsForBook(book.getIsbn13());
			String tag_str = "";
			for (Tag t: tags) {
				tag_str += t.toString() + " ";
			}
			tagList.add(tag_str);
		}
		// Populate the list into a variable that will be stored in the session
		request.getSession().setAttribute("books", bookList);
		request.getSession().setAttribute("tags", tagList);
		request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
	}
}
