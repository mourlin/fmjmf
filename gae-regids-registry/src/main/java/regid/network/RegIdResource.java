package regid.network;

import org.restlet.representation.Representation;

public interface RegIdResource {

	public abstract Representation create(Representation rep) throws Exception;

	public abstract Representation read(Representation rep) throws Exception;

	public abstract Representation update(Representation rep) throws Exception;

	public abstract Representation delete(Representation rep) throws Exception;

	public abstract Representation search();

}