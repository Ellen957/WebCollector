package Model;

public class doubanBook {
	private String bookName;
	private String author;
	private String publish;
	private String score;
	private String publishTime;
	private String price;
	private String ISBN;
	private String descrip;
	private String authorDes;
	
	public doubanBook(){}
	
	public doubanBook(String bookname,String author, String publish, String score,
			String publishTime, String price, String iSBN, String descrip,
			String authorDes) {
		super();
		this.bookName = bookname;
		this.author = author;
		this.publish = publish;
		this.score = score;
		this.publishTime = publishTime;
		this.price = price;
		ISBN = iSBN;
		this.descrip = descrip;
		this.authorDes = authorDes;
	}
	
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getAuthorDes() {
		return authorDes;
	}
	public void setAuthorDes(String authorDes) {
		this.authorDes = authorDes;
	}
	
	
}
