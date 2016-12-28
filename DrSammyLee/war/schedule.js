/**
 * Controls Schedule object
 */
	$(function(){ //load the script
		
	
 //initialize the schedule
  $('#schedule').fullCalendar({
	  defaultView:'agendaWeek', firstDay:1, minTime:"07:00:00", maxTime:"17:00:00",
	  contentHeight:function(){return Math.floor($(window).height() * 0.8);},
	  header:{center:'agendaWeek,agendaDay,listWeek' },columnFormat:'dddd',
	  editable:true,droppable:true,
	  //now handle view change call backs
	  viewRender:function(view, element){
		  if(view.name=="agendaWeek"){
			  $('#schedule').text("Weekly Schedule");
		  }else if(view.name=="agendaDay"){
			  $('#schedule').text("Daily Schedule");
		  }else{
			  $('#schedule').text("Schedule");
		  }
		  
	  },
	  //now handle object update events
	  eventDrop:function(){},
	  eventResize:function(){},
	  //create a new event on dayClick
	  dayClick:function(date, jsEvent, view){
		  var newItem = new ScheduleItem();
		showScheduleForm(newItem);
		  
	  } 
  });
  
  //initialize the color picker for the form
  $("input[type=color]").spectrum({
	    showPaletteOnly: true,
	    togglePaletteOnly: true,
	    togglePaletteMoreText: 'more',
	    togglePaletteLessText: 'less',
	    color: 'red',
	    palette: [
	        ['DimGray', 'Navy', 'Purple', 'Crimson','RedOrange', 'DarkGoldenRod','DarkGreen'],
	        ['Gray', 'Blue', 'Violet', 'Red', 'Orange', 'GoldenRod', 'Green']
	    ]
	});
   
  //see if roster schedule has been loaded
  if(!window.schedule){
	  //if not load it
	  //show loading icon/////
	  showLoader();
	  $.get('/' + window.rosterId +'/schedule', function(data,status){
		  if(data){//so if data isn't null
		  window.schedule = data;
		  }else{
			  window.schedule = new Schedule();
		  }
		 //end the loading icon 
		  hideLoader();
	  });///end get schedule
  }//end if !window.schedule/////
  
	});// end load script ///////////
  
  //access to Schedule object
  function Schedule(){
	  this.id = '';
	  this.items = [];
	  this.type = 'weekly'; // weekly or daily
	  this.addItem = function(item){
		items.push(item);  
	  };
  }
  //access to ScheduleItem
  function ScheduleItem(){
	 	this.id = '';
		this.title = '';
		this.start = '';
		this.end = '';
		this.constraint = '';
		this.bacgroundColor = '';
		this.borderColor = '';
		//array of string
		this.className = null;
		this.textColor = '';
		this.color = '';
  }
  
  //schedule form
  function showScheduleForm(item){
	  $('.scheduleModal').modal('open');
	  $('#scheduleForm').data('item', item);
  }
  
  function hideScheduleForm(){
	  $('.scheduleModal').modal('close');
	  $('#scheduleForm').get(0).rest();
  }
  
  function saveSchedule(){
	  $.ajax({
		  
	  });
  }
	  
	function showLoader(){
		
	}
	
	function hideLoader(){
		
	}
  
  
