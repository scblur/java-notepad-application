import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

class Editor extends WindowAdapter implements ActionListener,TextListener
{
	Frame f, fp, ff, ffr;
	MenuBar mb;
	Menu m1, m2;
	MenuItem nw, opn, sv, svas, ext, fnd, fndr;
	Button svp, dsvp,cls;
	TextArea t;
	String path, name;
	String check="";
	TextField fndp,fndp1, rplc;
	Button findw, findw1, replce, replceAll, clse, clse1;
	String fle;
	String selection;
	int start,start1, end1, last;
	boolean flag, saved=false;
	
	

	public Editor()
	{
		
		f = new Frame();
		f.setSize(800,600);
	
		//Font fn = new Font("Arial",Font.BOLD, 20);
		mb = new MenuBar();
		m1 = new Menu("File");
		m2 = new Menu("Edit");
		//m1.setFont(fn);
		//m2.setFont(fn);

		nw = new MenuItem("New");
		opn = new MenuItem("Open");
		sv = new MenuItem("Save");
		svas = new MenuItem("Save As");
		ext = new MenuItem("Exit");
		fnd = new MenuItem("Find");
		fndr = new MenuItem("Find & Replace");
		nw.addActionListener(this);
		opn.addActionListener(this);
		sv.addActionListener(this);
		svas.addActionListener(this);
		ext.addActionListener(this);
		fnd.addActionListener(this);
		fndr.addActionListener(this);

		//nw.setFont(fn);
		//opn.setFont(fn);
		//sv.setFont(fn);
		//svas.setFont(fn);
		//ext.setFont(fn);
		//fnd.setFont(fn);
		//fndr.setFont(fn);
		m1.add(nw);
		m1.add(opn);
		m1.add(sv);
		m1.add(svas);
		m1.addSeparator();
		m1.add(ext);

		m2.add(fnd);
		m2.add(fndr);

		mb.add(m1);
		mb.add(m2);

		f.setMenuBar(mb);
		
		t = new TextArea();
		//t.setFont(fn);
		f.add(t);

		f.setVisible(true);


		fp = new Frame();
		fp.setSize(400,300);
		Label l3 = new Label(" Do you want to save the changes...?");

		//l3.setFont(fn);
		fp.add(l3);
		Panel p = new Panel();
		svp = new Button("Save");
		dsvp = new Button("Don't Save");
		cls = new Button("Close");
		p.add(svp);
		p.add(dsvp);
		p.add(cls);	
		svp.addActionListener(this);
		dsvp.addActionListener(this);
		cls.addActionListener(this);

		
		//svp.setFont(fn);
		//dsvp.setFont(fn);
		//cls.setFont(fn);

		fp.add(p,"South");


		ff = new Frame();
		ff.setSize(400,300);
		ff.setLayout(new GridLayout(3,1));
		Label l4 = new Label("Find");
		//l4.setFont(fn);
		ff.add(l4);
		fndp = new TextField();
		ff.add(fndp);
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(1,2));
		findw = new Button("Find Next");
		//findw.setFont(fn);
		clse = new Button("Close");
		//clse.setFont(fn);
		p1.add(findw);
		p1.add(clse);
		ff.add(p1);
		
		findw.addActionListener(this);
		clse.addActionListener(this);


		ffr = new Frame();
		ffr.setSize(500,300);
		ffr.setLayout(new GridLayout(5,1));
		Label l5 = new Label("Find");
		//l5.setFont(fn);
		ffr.add(l5);
		fndp1 = new TextField();
		ffr.add(fndp1);
		Label l6 = new Label("Replace with");
		//l6.setFont(fn);
		ffr.add(l6);
		rplc = new TextField();
		ffr.add(rplc);
		Panel p2 = new Panel();
		p2.setLayout(new GridLayout(1,4));
		findw1 = new Button("Find Next");
		//findw1.setFont(fn);
		replce = new Button("Replace");
		//replce.setFont(fn);
		replceAll = new Button("Replace All");
		//replceAll.setFont(fn);
		clse1 = new Button("Close");
		//clse1.setFont(fn);
		p2.add(findw1);
		p2.add(replce);
		p2.add(replceAll);
		p2.add(clse1);
		ffr.add(p2);
		
		replce.addActionListener(this);
		replceAll.addActionListener(this);
		findw1.addActionListener(this);
		clse1.addActionListener(this);

		t.addTextListener(this);

		f.addWindowListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{

		if(e.getSource()==nw)
		{
			newcheck();
		}

		if(e.getSource()==opn)
		{
			opencheck();
		}

		if(e.getSource()==svas)
		{
			saved = true;			
			saveas();
		}

		if(e.getSource()==sv)
		{
			saved = true;
			save();	
		}

		if(e.getSource()==svp)
		{
			saved = true;
			fp.setVisible(false);
			fp.dispose();
			save();
			if(check.equals("exit"))
			{
				f.setVisible(false);
				f.dispose();
			}
			else
			{
				if(name != null)
				{					
					t.setText("");
					if(check.equals("open"))
						open();
				}
			}
		}
		if(e.getSource()==dsvp)
		{
			fp.setVisible(false);
			fp.dispose();
			if(check.equals("exit"))
			{
				f.setVisible(false);
				f.dispose();
			}
			else
			{					
					t.setText("");
					if(check.equals("open"))
						open();
			}
		}
		if(e.getSource()==cls)
		{	
			fp.setVisible(false);
			fp.dispose();

		}

		if(e.getSource()==ext)
		{
			exitt();
		}

		if(e.getSource()==fnd)
		{
			//System.out.println("in find menu button");
			ff.setVisible(true);
		}

		if(e.getSource()==findw)
		{
			Pattern p1 = Pattern.compile("\r\n");
			Matcher m1 = p1.matcher(t.getText());
			if(m1.find())
				t.setText(m1.replaceAll("\n"));
			
			findValuee();
		}
	
		if(e.getSource()==findw1)
		{
			Pattern p1 = Pattern.compile("\r\n");
			Matcher m1 = p1.matcher(t.getText());
			if(m1.find())
				t.setText(m1.replaceAll("\n"));
			findValueee();
		}

		if(e.getSource()==fndr)
		{
			ffr.setVisible(true);
		}

		if(e.getSource()==replce)
		{
			Pattern p1 = Pattern.compile("\r\n");
			Matcher m1 = p1.matcher(t.getText());
			if(m1.find())
				t.setText(m1.replaceAll("\n"));
			findValueee1();
			replacee();
		}

		if(e.getSource()==replceAll)
		{
			replaceeAll();
		}

		if(e.getSource()==clse)
		{
			ff.setVisible(false);
			ff.dispose();
			fle = null;
			start = end1 =0;
			last =0;
			//idontknow = 0;
		}

		if(e.getSource()==clse1)
		{
			ffr.setVisible(false);
			ffr.dispose();
			fle = null;
			start = end1 =0;
			last =0;
			//count = 0;
		}
	}

	public void textValueChanged(TextEvent ta)
	{
		if(ta.getSource()==t)
		{
			if((check.equals("open"))||(check.equals("new")))
			{
				saved = true;
				check="";
			}
			else
			{
			
			saved = false;
			
			}
			System.out.println("Inside txt event.....");
			System.out.println(saved+"");
		}
	}

	public void windowClosing(WindowEvent wa)
	{
		/*Window w = wa.getWindow();
		w.setVisible(false);
		w.dispose();
		System.exit(1);*/
		exitt();
	}
	public void saveas()
	{
		FileDialog fd = new FileDialog(f,"Save",FileDialog.SAVE);
		fd.setVisible(true);
			
		name = fd.getFile();
		path = fd.getDirectory();
		if(name!=null)
		{
			try
			{
				File f = new File(path+name+".txt");
			
				FileWriter fw =new FileWriter(f);

				fw.write(t.getText());	
					fw.close();
			}

			catch(Exception e2)
			{
				//System.out.println(e2.getMessage()+"");
			}
		}
	}

	public void save()
	{
		if((name==null)&&(path==null))
				saveas();
		else
		{
			try
			{
				File f = new File(path+name);
			
				FileWriter fw =new FileWriter(f);
				fw.write(t.getText());	
				fw.close();
			}

			catch(Exception e2)
			{
				//System.out.println(e2.getMessage()+"");
			}
		}
	
		

	}

	public void opencheck()
	{
		check = "open";

		if((t.getText()).equals(""))
		{
			open();
			
		}

		else
		{
			if(saved == true)
			{
				open();
			}
			else
			{
				
				fp.setVisible(true);
			}
		}
		
	}

	public void newcheck()
	{
		check = "new";
		
		if((t.getText()).equals(""))
		{
			name=null;
			path=null;
		}

		else
		{
			if(saved == true)
			{
				t.setText("");

				name=null;
				path=null;
			}
			else
				{
				fp.setVisible(true);
			}
		}

	}

	public void exitt()
	{
		check = "exit";
		if((t.getText()).equals(""))
		{
			
			f.setVisible(false);
			f.dispose();
			System.exit(1);
		}

		else
		{
			if(saved == true)
			{
				f.setVisible(false);
				f.dispose();
				System.exit(1);
	
			}
			else
				fp.setVisible(true);
		}

	}


	public void open()
	{
		
		FileDialog fd = new FileDialog(f,"Open",FileDialog.LOAD);
		fd.setVisible(true);

		//if((name==null)&&(path==null))
		//		return;
		//else
		//{	

		name = fd.getFile();
		path = fd.getDirectory();

		t.setText("");

		try
		{
			File f = new File(path+name);
		
			BufferedReader fr = new BufferedReader(new FileReader(f));
			String str, wholeText="";
			//StringBuffer wholeText= new StringBuffer();
			while((str=fr.readLine())!=null)
				wholeText = wholeText+str+"\n";
			t.setText(wholeText);
				//t.appendText(str+"\n");	
			fr.close();
		}

		catch(Exception e1)
		{
			//System.out.println(e1.getMessage()+"");
		}
		System.out.println(saved+"");
		saved = true;
		System.out.println(saved+"");
		//}
	}

	public void findValuee()
	{
		

			fle = t.getText();
			start = t.getSelectionEnd();
			t.selectAll();
			if(start==(t.getSelectionEnd()))
			{
				System.out.println("word not found");
				return;
			}
		String s = fndp.getText();
		Pattern p = Pattern.compile(s);
		Matcher m = p.matcher(fle);
	
		if(m.find(start))
		{
			
			t.requestFocus();
			t.select(m.start(),m.end());
			//start = m.end();
			System.out.println(m.start()+"\t"+m.end()+"\t"+m.group());
			flag = true;
		}
		else
			flag = false;
		
	}

	public void findValueee()
	{
		fle = t.getText();
		start1 = t.getSelectionEnd();
		t.selectAll();
		if(start1==(t.getSelectionEnd()))
		{
			System.out.println("word not found");
			return;
		}
		
		String s = fndp1.getText();
		Pattern p = Pattern.compile(s);
		Matcher m = p.matcher(fle);
	
		if(m.find(start1))
		{
			
			t.requestFocus();

			t.select(m.start(),m.end());
			start1 = m.start();
			end1= m.end();
			//selection = t.getSelectedText();
			System.out.println(m.start()+"\t"+m.end()+"\t"+m.group());
			flag = true;
		}
		else
		{
			flag = false;
		}
	}

	public void findValueee1()
	{
		fle = t.getText();
		start1 = t.getSelectionStart();
		t.selectAll();
		if(start1==(t.getSelectionEnd()))
		{
			System.out.println("word not found");
			return;
		}
		
		String s = fndp1.getText();
		Pattern p = Pattern.compile(s);
		Matcher m = p.matcher(fle);
	
		if(m.find(start1))
		{
			
			t.requestFocus();

			t.select(m.start(),m.end());
			start1 = m.start();
			end1= m.end();
			//selection = t.getSelectedText();
			System.out.println(m.start()+"\t"+m.end()+"\t"+m.group());
			flag = true;
		}
		else
		{
			flag = false;
		}
	}

	public void replacee()
	{
		if(flag==true)
		{
			
			String q = rplc.getText();
			t.replaceText(q,start1,end1);
			
			
		}

	}

	public void replaceeAll()
	{

		if(flag==true)
		{
		fle = t.getText();
		String result = null;
		String s = fndp1.getText();
		String q = rplc.getText();
		Pattern p = Pattern.compile(s);
		Matcher m = p.matcher(fle);
		if(m.find())
			result = m.replaceAll(q);
		if(result!=null)
			t.setText(result);
		}
	}
	public static void main(String args[])
	{
		Editor e = new Editor();
	}
}