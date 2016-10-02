package sai.norms.npl.nopl2sai;

/**
 * All the classes that want to be a listener for the institutional facts related to groups must implement this interface 
 * @author maiquel
 *
 */

public interface IGroup2SaiListener {
	
	public void sai_play(String Agent, String Role, String Group);
	public void sai_responsible(String group, String scheme);

}
