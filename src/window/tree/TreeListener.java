package window.tree;

import de.matthiasmann.twl.TreeTable;
import de.matthiasmann.twl.model.TableSingleSelectionModel;
import entity.Camera;
import entity.Entity;
import entity.EntityList;

public class TreeListener implements Runnable{
	TreeTable tree;
	TableSingleSelectionModel selection;
	EntityList objects;
	
	public TreeListener(TreeTable tree, TableSingleSelectionModel selection, EntityList objects){
		this.tree = tree;
		this.selection = selection;
		this.objects = objects;
	}
	
	public void run() {
		int row = selection.getFirstSelected();
		if(row >= 0) {
			Node n = (Node)tree.getNodeFromRow(row);
			
			Entity newFocus = objects.getItem((String)n.getData(0));
			if( newFocus != null){
				((Camera)objects.getItem("linear_focus")).setPosition(newFocus.getPosition());
			}
		}
	}
}
