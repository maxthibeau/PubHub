package examples.pubhub.dao;
import java.util.List;
import examples.pubhub.model.Tag;

public class TestTagDAO {

  public static void main(String[] args){
    TagDAO dao = new TagDAOImpl();
    List<Tag> list = dao.getAllTagsForBook("1111111111111");

    for (int i = 0; i < list.size(); i++){
      Tag t = list.get(i);
      System.out.println(t);
    }
  }
}