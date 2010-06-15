Themplator
=======================

Wicket-like t(h)emplating library written in Java and based on StAX. It is in an earlier than early stage 
and only offers a couple of controlling bricks, namely :

* Label: similar to Wicket's Label component, it is used to set an element's text
* Repeater and ListRepeater: to repeat an element
* InjectMarkup: to inject markup from a seperate stream into the result

Themplator also supports hiding bricks and body-only rendering.
 
Building
--------

You need a Java 5 (or newer) environment and Maven 2.0.9 (or newer) installed:

    $ mvn -v
    Apache Maven 3.0-beta-1 (r935667; 2010-04-19 19:00:39+0200)
    Java version: 1.6.0_20
    Java home: /usr/lib/jvm/java-6-sun-1.6.0.20/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "linux" version: "2.6.32-22-generic" arch: "amd64" Family: "unix"


You should now be able to do a full build of `themplator`:

    $ git clone git://github.com/jawher/themplator.git
    $ cd themplator
    $ mvn clean install

To use this library in your projects, add the following to the `dependencies` section of your
`pom.xml`:

    <dependency>
      <groupId>jawher</groupId>
      <artifactId>themplator</artifactId>
      <version>0.9-SNAPSHOT</version>
    </dependency>


Troubleshooting
---------------

Please consider using [Github issues tracker](http://github.com/jawher/themplator/issues) to submit bug reports or feature requests.


Using this library
------------------

Here is a sample showing the usage of themplator's very few templating capabilities implemented so far:

Given this markup :

    <?xml version="1.0" encoding="UTF-8" ?>
    <root>
    	<div thid="d">
    		<span style="color: aqua;" thid="s">hello world</span>
    	</div>
    	<h1 thid="invisible">title</h1>
    </root>



The above can be manipulated using themplator's bricks :
    
    List<String> data = Arrays.asList("one", "two", "three", "four");
    
    Themplate t = new Themplate();
    
    ListRepeater<String> d = new ListRepeater<String>("d",
    		new SimpleModel<List<String>>(data)) {
    
    	@Override
    	protected void populate(
    			themplator.bricks.ListRepeater.ListItem<String> item) {
    		InjectMarkup im = new InjectMarkup("s", Thest2.class
    				.getResourceAsStream("brick.html"));
    		im.add(new Label("s", item.getModel()));
    		item.add(im);
    	}
    
    };
    
    d.setRenderBodyOnly(true);
    t.add(d);
    
    Label label = new Label("invisible", new SimpleModel<String>("text"));
    label.setRenderBodyOnly(true);
    t.add(label);
    
    InputStream is = Thest2.class.getResourceAsStream("test0.html");
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    
    t.render(is, os);
    
    System.out.println(new String(os.toByteArray()));


to produce the following:

    <?xml version='1.0' encoding='UTF-8'?>
    <root>
    	<span style="color: aqua;" thid="s">
    		<ul class="menu">
    			<li class="active" thid="s">one</li>
    			<li>Item 1</li>
    		</ul>
    	</span>
    
    	<span style="color: aqua;" thid="s">
    		<ul class="menu">
    			<li class="active" thid="s">two</li>
    			<li>Item 1</li>
    		</ul>
    	</span>
    
    	<span style="color: aqua;" thid="s">
    		<ul class="menu">
    			<li class="active" thid="s">three</li>
    			<li>Item 1</li>
    		</ul>
    	</span>
    
    	<span style="color: aqua;" thid="s">
    		<ul class="menu">
    			<li class="active" thid="s">four</li>
    			<li>Item 1</li>
    		</ul>
    	</span>
    
    	text
    </root>

License
-------

See `LICENSE` for details.
