package regid.persistance;

import java.util.List;

public interface IFacade<P> {
	boolean create(P p);
	P read(long id);
	int update(P p);
	boolean delete(long id);
	List<P> searchAll();
}
