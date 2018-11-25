package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@NamedQueries({
@NamedQuery(name="ware.all",query="SELECT w FROM Ware w"),
@NamedQuery(name="ware.id",query="SELECT w FROM Ware w WHERE w.id=:wareId"),
@NamedQuery(name="ware.cat",query="SELECT w FROM Ware w WHERE w.category=:category"),
@NamedQuery(name="ware.price",query="SELECT w FROM Ware w WHERE w.price BETWEEN :num1 AND :num2"),
@NamedQuery(name="ware.name",query="SELECT w FROM Ware w WHERE w.name=:name"),
@NamedQuery(name="ware.com",query="SELECT c FROM Comment c WHERE c.id=:comId")
})
public class Ware {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private double price;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	private transient List<Comment> comments = new ArrayList<Comment>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@OneToMany(mappedBy="ware", cascade=CascadeType.ALL, orphanRemoval=true)
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
