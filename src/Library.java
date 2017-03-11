import java.util.LinkedList;

public class Library implements MaxTagValue {

	String libraryName;
	LinkedList<Book> books;

	public Library(String libraryName) {
		this.libraryName = libraryName;
		books = new LinkedList<Book>();
	}

	// Get max value tag
	public int findMaximumValueTag() {
		int maxElement = -100;
		for (final Book book : books) {
			if (book.getValueTag() > maxElement) {
				maxElement = book.getValueTag();
			}
		}

		return maxElement;
	}

	/**
	 * Rent request
	 * 
	 * @param wanted
	 * @param requestDate
	 * @param dueDate
	 * @return true/false
	 */
	public boolean rentRequest(Book wanted, String requestDate, String dueDate) {

		// Validate request book
		if (wanted == null || wanted.getBookName() == null) {
			System.err.println("Requested book is invalid.");
			return false;
		}

		// Validate date format
		if (!Helper.isValidDate(requestDate) || !Helper.isValidDate(dueDate)) {
			System.err.println("Rent date or Due date is not valid.");
			return false;
		}

		for (int i = 0; i < books.size(); i++) {
			final Book book = books.get(i);
			// In case library owned the book
			if (book != null && wanted.getBookName().equalsIgnoreCase(book.getBookName())) {
				boolean isRented = book.rentBook(requestDate, dueDate, this);
				return isRented;
			}
		}

		return false;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public LinkedList<Book> getBooks() {
		return books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}
	
	public void editBook(int index, Book book) {
		books.set(index, book);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Library = ").append(libraryName).append("\n").append("[\n");
		for (final Book bk : books) {
			sb.append(bk).append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

}
