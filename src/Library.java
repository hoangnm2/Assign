import java.util.LinkedList;

public class Library<T> implements MaxTagValue {

	String libraryName;
	LinkedList<T> books;

	public Library(String libraryName) {
		this.libraryName = libraryName;
		books = new LinkedList<T>();
	}

	// Get max value tag
	public int findMaximumValueTag() {
		int maxElement = -100;
		for (int i = 0; i < books.size(); i++) {
			final Book book = (Book) books.get(i);
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
			final Book book = (Book) books.get(i);
			// In case library owned the book
			if (book != null && wanted.getBookName().equalsIgnoreCase(book.getBookName())) {
				boolean isRented = book.rentBook(requestDate, dueDate, this);
				if (isRented) {
					System.out.println(book + " is rented successfully.");
				} else {
					System.out.println(book + " is not available and cant not be rented.");
				}
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

	public LinkedList<T> getBooks() {
		return books;
	}

	public void addBook(T book) {
		this.books.add(book);
	}

	/**
	 * To edit a book in library at current index
	 * @param index
	 * @param book
	 */
	public void editBook(int index, T book) {
		books.set(index, book);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Library = ").append(libraryName).append("\n").append("[\n");
		for (int i = 0; i < books.size(); i++) {
			final Book bk = (Book) books.get(i);
			sb.append(bk).append("\n");
		}
		sb.append("]\n");
		return sb.toString();
	}

}
