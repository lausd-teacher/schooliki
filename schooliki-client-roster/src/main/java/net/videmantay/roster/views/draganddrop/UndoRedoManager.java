package net.videmantay.roster.views.draganddrop;

import java.util.Stack;

import com.google.gwt.core.client.GWT;

public class UndoRedoManager {
	
	public static final Integer MAX_STACK_SIZE = 10;
	
	Stack<Action> doStack;
	Stack<Action> redoStack;
	
	
	public UndoRedoManager(){
		doStack = new Stack<Action>();
		redoStack = new Stack<Action>();
	}
	
	
	
	public void recordAction(Action action){
		
		addTodoStack(action);
		
	}
	
	
	public void undoLastAction(){
		
		if(!doStack.empty()){

			Action action = doStack.pop();
			
			if(action instanceof FurnitureAddAction){
				FurnitureRemoveAction reverseAction = new FurnitureRemoveAction(action.getItem());
				GWT.log("undoing");
				reverseAction.exec();
				addToRedoStack(reverseAction);
			}else if(action instanceof FurnitureRemoveAction){
				FurnitureAddAction reverseAction = new FurnitureAddAction(action.getItem());
				reverseAction.exec();
				addToRedoStack(reverseAction);
			}
		
		}
		
	}
	
	
	public void redoLastAction(){
		
		
	if(!redoStack.empty()){
        Action action = redoStack.pop();
		
		if(action instanceof FurnitureAddAction){
			FurnitureRemoveAction reverseAction = new FurnitureRemoveAction(action.getItem());
			reverseAction.exec();
			addTodoStack(reverseAction);
		}else if(action instanceof FurnitureRemoveAction){
			FurnitureAddAction reverseAction = new FurnitureAddAction(action.getItem());
			reverseAction.exec();
			addTodoStack(reverseAction);
		}
	}
		
		
	}
	
	
	
	private void addTodoStack(Action action){
		if(doStack.size() < MAX_STACK_SIZE) {
			GWT.log("pushing into stack");
			doStack.push(action);
		}else{
			removeOldestItem(doStack);
			doStack.push(action);
			
		}
	}
	
	
	private void addToRedoStack(Action action){
		if(redoStack.size() < MAX_STACK_SIZE) {
			redoStack.push(action);
		}else{
			removeOldestItem(redoStack);
			redoStack.push(action);
		}
	}
	
	
	private void removeOldestItem(Stack<Action> doStack){
		Stack<Action> tempStack = new Stack<Action>();
		
		//retrieve all elements execpt the first one, which is the one we want to remove
		for(int i = doStack.size() - 1; i < 1; i--){
			Action action = doStack.pop();
			tempStack.push(action);
		}
		
		//put them back into doStack
        for(int i = 0 ; i < tempStack.size(); i++){
			Action action = tempStack.pop();
			doStack.push(action);
		}
		
		
		
	}
	

}
